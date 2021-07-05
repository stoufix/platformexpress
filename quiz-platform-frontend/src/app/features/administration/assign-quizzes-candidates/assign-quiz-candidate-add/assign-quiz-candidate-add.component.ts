import { Component, OnInit } from '@angular/core';
import { GenericService } from 'src/app/core/services/generic.service';
import { User } from 'src/app/shared/models/user';
import swal from 'sweetalert2';
import { AuthenticationService } from 'src/app/core/services/authentication.service';
import { Router } from '@angular/router';
import { PageClient } from '@models/page-client';
import { AssignedQuizOnlineInterview } from '@models/assigned-quiz-online-interview';
import { AssignedQuizOnlineInterviewId } from '@models/assigned-quiz-online-interview-id';
import { OnlineInterview } from '@models/online-interview';

@Component({
  selector: 'app-assign-quiz-candidate-add',
  templateUrl: './assign-quiz-candidate-add.component.html',
  styleUrls: ['./assign-quiz-candidate-add.component.css']
})
export class AssignQuizCandidateAddComponent implements OnInit {

  listOfCandidates: any = [];
  listOfQuizzes: any = [];
  listOfSelectedCandidates: Array<OnlineInterview> = [];
  assignedQuizOnlineInterview: AssignedQuizOnlineInterview;
  assignedQuizOnlineInterviewId: AssignedQuizOnlineInterviewId;
  listIdOfSelectedCandidates = [];
  listOfUnselectedCandidates: any = [];
  listOfSelectedQuizzes = [];
  listIdOfSelectedQuizzes = [];
  username: string;
  userConnected: User;
  selectButton: any;
  testIfNull: boolean;
  passingAt: Date;
  // For pagination
  pageClient: PageClient = new PageClient();
  pageClientQuizzes: PageClient = new PageClient();
  totalQuizzes: number;
  selectedPageQuizzes: number;
  itemQuizzes = 5;
  total: number;
  selectedPage: number;
  item = 5;
  listOfPassedQuizzes: any = [];
  listNameOfCandidates: any = [];
  listPassedQuizToDelete: any = [];
  listIdOfPassedQuizzes: any = [];
  // List of onlineInterviews
  listOfOnlineInterviews: any = [];
  // List if candidates with assigned quizzes
  listNameOfCandidatesWithAssignedQuizzes: any = [];
  count = 0;
  // Number of assigned quizzes
  countAssignedQuiz = 0;
  passedQuiz: any;
  constructor(private genericService: GenericService, private authenticationService: AuthenticationService, private router: Router) { }

  ngOnInit() {
    this.listOfCandidates = [];
    this.listOfQuizzes = [];
    this.getListOfCandidates();
    this.getListOfQuizzes();
    this.getUsername();
    this.getConnectedUser();
    this.CheckIfListIsEmpty();
    this.passingAt = new Date();
  }

  onSelect(n: number) {
    this.selectedPage = n - 1;
    this.getListOfCandidates();
  }

  onSelectQuizzes(n: number) {
    this.selectedPageQuizzes = n - 1;
    this.getListOfQuizzes();
  }

  getItems(i: number) {
    this.item = i;
    this.onSelect(1);
    this.getListOfCandidates();
  }

  getItemsQuizzes(i: number) {
    this.itemQuizzes = i;
    this.onSelectQuizzes(1);
    this.getListOfQuizzes();
  }

  /* Get list of candidates */
  getListOfCandidates() {
    this.genericService.getGenericPage('/interviews', this.selectedPage, this.item).subscribe(data => {
      this.pageClient = data;
      this.listOfCandidates = this.pageClient.content;
    });
  }

  /* Get list of quizzes */
  getListOfQuizzes() {
    this.genericService.getGenericPage('/quizzes', this.selectedPageQuizzes, this.itemQuizzes).subscribe(data => {
      this.pageClientQuizzes = data;
      this.totalQuizzes = this.pageClientQuizzes.totalElements;
      this.listOfQuizzes = this.pageClientQuizzes.content;
    });
  }

  /* Select list of candidates */
  selectCandidate(onlineInterview) {
    console.log('display onlineInterview');
    console.log(onlineInterview);
    this.listOfSelectedCandidates = this.listOfSelectedCandidates.concat(onlineInterview);
    this.listIdOfSelectedCandidates = this.listIdOfSelectedCandidates.concat(onlineInterview.id);
    const index = this.listOfCandidates.indexOf(onlineInterview);
    /* this.listOfCandidates.splice(index, 1); */
    this.CheckIfListIsEmpty();
    // Gets list of passed quizzes by online interview
    this.genericService.getGenericById('/interviews/passedQuizzes', onlineInterview.id).subscribe(passedQuiz => {
      this.listOfPassedQuizzes = this.listOfPassedQuizzes.concat(passedQuiz);
    });
  }
  /* Select quiz  */
  selectQuiz(quiz) {
    this.listNameOfCandidates = [];
    this.listPassedQuizToDelete = [];
    this.listNameOfCandidatesWithAssignedQuizzes = [];
    this.listOfSelectedCandidates.forEach(onlineInterview => {
      onlineInterview.assignedQuizzes.forEach(assignedQuiz => {
        if (quiz.id === assignedQuiz.id.quizId) {
          this.countAssignedQuiz += 1;
          this.listNameOfCandidatesWithAssignedQuizzes = this.listNameOfCandidatesWithAssignedQuizzes.concat(onlineInterview
            .application.candidate.firstName + ' ' + onlineInterview
              .application.candidate.lastName);
        }
      });
    });
    this.listNameOfCandidates = [];
    this.listOfPassedQuizzes.forEach(passedQuiz => {
      if (quiz.id === passedQuiz.id.quizId) {
        this.count += 1;
        this.listPassedQuizToDelete = this.listPassedQuizToDelete.concat(passedQuiz);
        this.listNameOfCandidates = this.listNameOfCandidates.concat(passedQuiz.onlineInterview.application.candidate.
          firstName + ' ' + passedQuiz.onlineInterview
            .application.candidate.lastName);
      }
    });

    if (this.count !== 0 && this.countAssignedQuiz !== 0) {
      swal({
        title: 'Vous êtes Sur ?',
        text: 'Ce QCM est déja affecté à: ' + this.listNameOfCandidatesWithAssignedQuizzes +
          '\n Ce QCM est déja passé par ' + this.listNameOfCandidates,
        type: 'warning',
        showCancelButton: false,
      });
    } else if (this.countAssignedQuiz !== 0) {
      swal({
        text: 'Ce QCM est déja affecté à: ' + this.listNameOfCandidatesWithAssignedQuizzes,
        type: 'warning',
        showCancelButton: false,
      });
    } else if (this.count !== 0) {
      this.passedQuiz = quiz;
      swal({
        title: 'Vous êtes Sur ?',
        text: 'Ce QCM est déja passé par ' + this.listNameOfCandidates + ', voulez vous vraiment l\'affecter une autre fois',
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Oui',
        cancelButtonText: 'Non'
      }).then((result) => {
        if (result.dismiss !== swal.DismissReason.cancel && result.dismiss !== swal.DismissReason.backdrop) {
          this.listOfSelectedQuizzes = this.listOfSelectedQuizzes.concat(quiz);
          this.listIdOfSelectedQuizzes = this.listIdOfSelectedQuizzes.concat(quiz.id);
          this.CheckIfListIsEmpty();
          this.listPassedQuizToDelete.forEach(passedQuizToDelet => {
            this.genericService.deleteWithBody('/onlineInterviewQuizResult', { body: passedQuizToDelet.id }).subscribe(data => { })
            this.listOfPassedQuizzes.forEach(passedQuiz => {
              if (passedQuiz.id === passedQuizToDelet.id) {
                const index = this.listOfPassedQuizzes.indexOf(passedQuiz);
                this.listOfPassedQuizzes.splice(index, 1);
              }
            });
          });
        }
      });
    } else {
      this.listOfSelectedQuizzes = this.listOfSelectedQuizzes.concat(quiz);
      this.listIdOfSelectedQuizzes = this.listIdOfSelectedQuizzes.concat(quiz.id);
      this.CheckIfListIsEmpty();
    }
    this.count = 0;
    this.countAssignedQuiz = 0;

  }

  /* Save assign candidate quiz  */
  affectQuizToUser(passingAt: Date) {
    for (const selectedUser of this.listOfSelectedCandidates) {
      selectedUser.assignedQuizzes = new Array<AssignedQuizOnlineInterview>();
      for (const selectedQuizId of this.listIdOfSelectedQuizzes) {
        this.assignedQuizOnlineInterview = new AssignedQuizOnlineInterview();
        this.assignedQuizOnlineInterviewId = new AssignedQuizOnlineInterviewId();
        this.assignedQuizOnlineInterviewId.quizId = selectedQuizId;
        this.assignedQuizOnlineInterviewId.onlineInterviewId = selectedUser.id;
        this.assignedQuizOnlineInterview.assignedBy = this.userConnected;
        this.assignedQuizOnlineInterview.passingAt = passingAt;
        this.assignedQuizOnlineInterview.id = this.assignedQuizOnlineInterviewId;
        selectedUser.assignedQuizzes = selectedUser.assignedQuizzes.concat(this.assignedQuizOnlineInterview);
      }
    }
    this.genericService.createGenericList('/assignquizonlineinterview', this.listOfSelectedCandidates).subscribe(data => {
      if (data.error === false) {
        swal({
          position: 'top-end',
          type: 'success',
          title: data.value,
          showConfirmButton: false,
          timer: 1500
        });
        this.router.navigate(['/administration/assign-quizzes-candidates']);
      } else {
        swal({
          title: 'Erreur!',
          text: data.value,
          type: 'error',
          confirmButtonText: 'Ok'
        });
      }
    }
    );
  }

  /* Get the username of the connected user */
  getUsername() {
    this.username = this.authenticationService.getUsername();
  }

  /* Get connected user by his username */

  getConnectedUser() {
    this.genericService.getUserByUsername('/users/username', this.username).subscribe(data => {
      this.userConnected = data.value;
    });
  }

  /* Remove selected candidate */
  removeSelectedCandidate(selectedCandidate) {
    const index = this.listOfSelectedCandidates.indexOf(selectedCandidate);
    this.listOfSelectedCandidates.splice(index, 1);
    this.listIdOfSelectedCandidates.splice(index, 1);
    /* this.listOfCandidates = this.listOfCandidates.concat(selectedCandidate); */
  }

  /* Remove selected quiz */
  removeSelectedQuiz(selectedQuiz) {
    const index = this.listOfSelectedQuizzes.indexOf(selectedQuiz);
    this.listOfSelectedQuizzes.splice(index, 1);
    this.listIdOfSelectedQuizzes.splice(index, 1);
    /* this.listOfQuizzes = this.listOfQuizzes.concat(selectedQuiz); */
    this.CheckIfListIsEmpty();
  }

  CheckIfListIsEmpty() {
    if (this.listOfSelectedCandidates.length === 0 || this.listOfSelectedQuizzes.length === 0) {
      this.testIfNull = true;
    } else {
      this.testIfNull = false;
    }
  }

  testIfCandidateNotExistInSelectedList(onlineInterviewId) {
    if (this.listIdOfSelectedCandidates.includes(onlineInterviewId)) {
      return false;
    } else {
      return true;
    }
  }

  testIfQuizNotExistInSelectedList(quizId) {
    if (this.listIdOfSelectedQuizzes.includes(quizId)) {
      return false;
    } else {
      return true;
    }
  }
}
