import { Component, OnInit, Input } from '@angular/core';
import { GenericService } from '@services/generic.service';
import { Quiz } from '@models/quiz';
import { PageClient } from '@models/page-client';
import { User } from '@models/user';
import { AuthenticationService } from '@services/authentication.service';
import { OnlineInterview } from '@models/online-interview';
import { AssignedQuizOnlineInterview } from '@models/assigned-quiz-online-interview';
import { AssignedQuizOnlineInterviewId } from '@models/assigned-quiz-online-interview-id';
import Swal from 'sweetalert2';
import { Privilege } from '@models/privilege';
import { isNullOrUndefined } from 'util';
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'app-assign-quiz-candidate-update',
  templateUrl: './assign-quiz-candidate-update.component.html',
  styleUrls: ['./assign-quiz-candidate-update.component.css']
})

export class AssignQuizCandidateUpdateComponent implements OnInit {

  // Pagination params
  pageClient: PageClient = new PageClient();
  total: number;
  selectedPage = 0;
  item = 5;
  // List of privileges by user
  ListOfPrivilegesByUser: Array<Privilege> = [];
  listQuizzes: Quiz[];
  // For simple search
  searchInput: String = '';
  // OnlineInterview To update
  onlineInterviewToUpdate: OnlineInterview = new OnlineInterview();
  // List quizzes added
  listQuizzesAdded: Array<Quiz> = [];
  // List id of selected quizzes
  listIdSelectedQuizzes = [];
  // List id of assigned quizzes
  listIdAssignedQuizzes: Array<number> = [];
  // list of assigned quiz onlineInterview added
  listAssignedQuizOnlineInterviewAdded: Array<AssignedQuizOnlineInterview> = [];
  // Assign date
  passingAt: Date;
  // Connected user params
  username: string;
  userConnected: User;

  constructor(private genericService: GenericService,
    private authenticationService: AuthenticationService, private route: ActivatedRoute, private router: Router) {
  }

  ngOnInit() {
    this.getOnlineInterviewToUpdate();
    this.getQuizzes();
    this.getUsername();
    this.getConnectedUser();
    this.passingAt = new Date();
  }

  /** Get onlineInterview To update */
  getOnlineInterviewToUpdate() {
    const id = this.route.snapshot.params['id'];
    this.genericService.getGenericById('/interviews', id).subscribe(data => {
      this.onlineInterviewToUpdate = data.value;
      this.loadListIdSelectedQuizzes();
    });
  }

  /** Select Quiz */
  addSelectedQuiz(quiz: Quiz) {
    this.listQuizzesAdded = this.listQuizzesAdded.concat(quiz);
    this.listIdSelectedQuizzes = this.listIdSelectedQuizzes.concat(quiz.id);
  }

  /** Gets assigned quizzes */
  loadListIdSelectedQuizzes() {
    for (const quiz of this.onlineInterviewToUpdate.assignedQuizzes) {
      this.listIdAssignedQuizzes = this.listIdAssignedQuizzes.concat(quiz.id.quizId);
    }
    for (const addedQuiz of this.listQuizzesAdded) {
      this.listIdSelectedQuizzes = this.listIdSelectedQuizzes.concat(addedQuiz.id);
    }
  }

  /** Check exxistances of quizzes in the list  */
  testIfQuizNotExistInListAssignedQuizzes(idQuiz: number) {
    if (this.listIdSelectedQuizzes.includes(idQuiz) || (this.listIdAssignedQuizzes.includes(idQuiz))) {
      return false;
    } else {
      return true;
    }
  }

  /** Remove quiz from assign list */
  removeQuizFromNewListSelectedQuizzes(quiz: Quiz) {
    const index = this.listQuizzesAdded.indexOf(quiz);
    this.listQuizzesAdded.splice(index, 1);
    this.listIdSelectedQuizzes.splice(index, 1);
  }

  /** Get connected user's username */
  getUsername() {
    this.username = this.authenticationService.getUsername();
  }

  /** Get connected user by userName  */
  getConnectedUser() {
    this.genericService.getUserByUsername('/users/username', this.username).subscribe(data => {
      this.userConnected = data.value;
    });
  }

  /* Gets Quizzes based on privileges and visibility criteria */
  getQuizzes() {
    // to test the exist of SHOW_ALL_ACTIVITIE privilege
    function findPrivilegeActivities(privilege) {
      return privilege === 'SHOW_ALL_ACTIVITIES';
    }
    // gets user by username
    this.genericService.getUserByUsername('/users/username', this.authenticationService.getUsername())
      .subscribe(user => {
        // list of privileges of current user and save titles of privileges in a list
        user.value.role.privileges.forEach(element => {
          this.ListOfPrivilegesByUser.push(element.title);
        });
        // return the first item respecting condition of findPrivilegeActivities function
        const AllActivitiesAccess = this.ListOfPrivilegesByUser.find(findPrivilegeActivities);
        if (isNullOrUndefined(AllActivitiesAccess)) {
          // gets visible quizzes of activity related to authenticated user
          this.genericService.getGenericByAuthPage('/quizzes/allVisibleQuizzesByAuthActivity', 
          user.value.username, this.selectedPage, this.item)
            .subscribe(
              data => {
                this.pageClient = data;
                this.total = this.pageClient.totalElements;
                this.listQuizzes = this.pageClient.content;
              });
        } else {
          // gets all visible quizzes without activity criteria
          this.genericService.getGenericByAuthPage('/quizzes/allVisibleQuizzes', user.value.username, this.selectedPage, this.item)
            .subscribe(
              data => {
                this.pageClient = data;
                this.total = this.pageClient.totalElements;
                this.listQuizzes = this.pageClient.content;
              });
        }
      });
  }

  /* Pagination: Change page number */
  onSelect(pageNumber: number) {
    this.selectedPage = pageNumber - 1;
    if (this.searchInput !== '') {
      this.onSearch(this.searchInput);
    } else {
      this.getQuizzes();
    }
  }

  /** Simple search */
  onSearch(search: String) {
    // to test the exist of SHOW_ALL_ACTIVITIE privilege
    function findPrivilegeActivities(privilege) {
      return privilege === 'SHOW_ALL_ACTIVITIES';
    }
    if (this.searchInput !== '') {
      // Gets User by username
      this.genericService.getUserByUsername('/users/username', this.authenticationService.getUsername())
        .subscribe(user => {
          // Gets list of privileges of current user and save titles of privileges in a list
          user.value.role.privileges.forEach(element => {
            this.ListOfPrivilegesByUser.push(element.title);
          });
          // return the first item respecting condition of findPrivilegeActivities function
          const AllActivitiesAccess = this.ListOfPrivilegesByUser.find(findPrivilegeActivities);
          if (isNullOrUndefined(AllActivitiesAccess)) {
            // gets visible Quizzes of activity related to authenticated user
            this.genericService.searchGenericByAuthPage('/quizzes/searchWithActivity',
             user.value.username, search, this.selectedPage, this.item)
              .subscribe(
                data => {
                  if (this.selectedPage > (data.value.totalPages) - 1) {
                    this.onSelect((data.value.totalPages));
                  } else {
                    this.pageClient = new PageClient();
                    this.pageClient = data.value;
                    this.total = this.pageClient.totalElements;
                    this.listQuizzes = this.pageClient.content;
                  }
                });
          } else {
            // gets all visible quizzes without activity criteria
            this.genericService.searchGenericByAuthPage('/quizzes/searchWithoutActivity',
             search, user.value.username, this.selectedPage, this.item)
              .subscribe(
                data => {
                  if (this.selectedPage > (data.totalPages) - 1) {
                    this.onSelect((data.totalPages));
                  } else {
                    this.pageClient = new PageClient();
                    this.pageClient = data;
                    this.total = this.pageClient.totalElements;
                    this.listQuizzes = this.pageClient.content;
                  }
                });
          }
        });
    } else {
      this.getQuizzes();
    }
  }
  
  // save assign quiz onlineInterview
  updateOnlineInterview() {
    for (const quiz of this.listQuizzesAdded) {
      const assignedQuizOnlineInterviewAdded = new AssignedQuizOnlineInterview();
      const assignedQuizOnlineInterviewId = new AssignedQuizOnlineInterviewId();
      assignedQuizOnlineInterviewAdded.quiz = quiz;
      assignedQuizOnlineInterviewAdded.assignedBy = this.userConnected;
      assignedQuizOnlineInterviewAdded.passingAt = this.passingAt;
      assignedQuizOnlineInterviewId.onlineInterviewId = this.onlineInterviewToUpdate.id;
      assignedQuizOnlineInterviewId.quizId = quiz.id;
      assignedQuizOnlineInterviewAdded.id = assignedQuizOnlineInterviewId;
      this.listAssignedQuizOnlineInterviewAdded = this.listAssignedQuizOnlineInterviewAdded.concat(assignedQuizOnlineInterviewAdded);
    }
    this.onlineInterviewToUpdate.assignedQuizzes = this.onlineInterviewToUpdate.assignedQuizzes.
    concat(this.listAssignedQuizOnlineInterviewAdded);
    this.genericService.updateGeneric('/assignquizonlineinterview',
     this.onlineInterviewToUpdate.id, this.onlineInterviewToUpdate).subscribe(data => {
      if (data.error === false) {
        Swal({
          position: 'top-end',
          type: 'success',
          title: data.value,
          showConfirmButton: false,
          timer: 1500
        });
        this.router.navigate(['administration/assign-quizzes-candidates']);
      } else {
        Swal({
          title: 'Erreur!',
          text: data.value,
          type: 'error',
          confirmButtonText: 'ok'
        });
      }
    }
    );
  }
}
