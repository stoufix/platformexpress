import { Component, OnInit } from '@angular/core';
import { Question } from '@models/question';
import { ActivatedRoute, Router } from '@angular/router';
import { Proposition } from '@models/proposition';
import { QuizQuestion } from '@models/quiz-question';
import { Quiz } from '@models/quiz';
import { GenericService } from '@services/generic.service';
import { AuthenticationService } from '@services/authentication.service';
import Swal from 'sweetalert2';
import { PageClient } from '@models/page-client';
import { isNullOrUndefined } from 'util';
import { Technology } from '@models/technology';

@Component({
  selector: 'app-quiz-assign',
  templateUrl: './quiz-assign.component.html',
  styleUrls: ['./quiz-assign.component.css']
})

export class QuizAssignComponent implements OnInit {

  // Id of the update Quiz
  id: any;

  // List of questions to display into data table
  listQuestions: any;

  // Created quiz object
  quiz: Quiz = new Quiz();

  // Declaration of object Quiz to edit
  quizToEdit: Quiz = new Quiz;

  // List of questions to generate
  listOfGeneratedQuestions: any = [];

  // List of questions with image
  listQuestionsWithImage: any = [];

  // Details of question
  questionDetails: any = {};

  // List of affected questions
  listQuizQuestion: any = [];

  // QuizQuestion to affect
  quizQuestion: QuizQuestion = new QuizQuestion();

  // QuizQuestion to edit
  quizQuestionToEdit: QuizQuestion = new QuizQuestion();

  // To generate random quiz
  random: number;
  N: number;

  // To test if question exist in the list of affected questions
  exist: Boolean = false;

  // question with image
  questionWithImage: Question = new Question();

  // Declaration of objectt Question to create
  questionToAdd: Question = new Question();

  // Proposition of question(create and empty object)
  proposition: Proposition = new Proposition();

  // Proposition of question(edit and clone question)
  p: Proposition = new Proposition();

  // Declaration of object Question to clone
  questionToClone: Question = new Question();

  // Declaration of object proposition to clone
  propositionClone: Proposition = new Proposition();

  // Details of selected question to pass to details modal
  detailQuestion: Question = new Question();

  // List of technologies
  listTechnologies: Array<Technology> = [];

  // Pagination params
  pageClient: PageClient = new PageClient();
  total: number;
  selectedPage = 0;
  item = 5;

  // For simple search
  searchInput: String = '';

  // List of privileges by user
  ListOfPrivilegesByUser: any = [];

  // Visibility of question for adding new question
  visibilityValue: boolean;

  // Initial mark of the question
  mark: number;

  constructor(private route: ActivatedRoute, private genericService: GenericService, private authenticationService: AuthenticationService,
    private router: Router) {
  }

  ngOnInit() {
    this.mark = 0.25;
    this.getTechnologies();
    this.listQuestions = [];
    this.quiz = new Quiz();
    this.getCreatedQuiz();
  }


  /** the Create or update Quiz */
  getCreatedQuiz() {
    this.id = this.route.snapshot.paramMap.get('id');
    this.genericService.getGenericById('/quizzes', this.id)
      .subscribe(
        data => {
          if (data.error === false) {
            this.quiz = data.value;
            this.getQuestions();
            if (this.quiz.questions.length !== 0) {
              this.listQuizQuestion = this.quiz.questions;
            }
          } else {
            Swal({
              title: 'Erreur!',
              text: data.value,
              type: 'error',
              confirmButtonText: 'ok'
            });
          }
        });
  }

  /** Get Questions based on privileges and visibility criteria without images */
  getQuestions() {
    // Test the exist of SHOW_ALL_ACTIVITIE privilege
    function findPrivilegeActivities(privilege: any) {
      return privilege === 'SHOW_ALL_ACTIVITIES';
    }
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
          // gets visible Questions of activity related to authenticated user
          this.genericService.getGenericByAuthPageByTwoId('/questions/allVisibleQuestionsByAuthActivity', user.value.username,
            '/technology', '/degree', this.quiz.technology.id, this.quiz.degree.id, this.selectedPage, this.item)
            .subscribe(
              data => {
                this.listQuestionsWithImage = data.content;
                this.listQuestions = [];
                // gets list of questions without image
                this.removeImagesFromQuestoions(this.listQuestionsWithImage, this.listQuestions);
                this.pageClient = new PageClient();
                this.pageClient = data;
                this.total = this.pageClient.totalElements;
              });
        } else {
          // gets all visible questions without activity criteria
          this.genericService.getGenericByAuthPageByTwoId('/questions/allVisibleQuestions', user.value.username,
            '/technology', '/degree', this.quiz.technology.id, this.quiz.degree.id, this.selectedPage, this.item)
            .subscribe(
              data => {
                this.listQuestionsWithImage = data.content;
                this.listQuestions = [];
                // get list of questions without image
                this.removeImagesFromQuestoions(this.listQuestionsWithImage, this.listQuestions);
                this.pageClient = new PageClient();
                this.pageClient = data;
                this.total = this.pageClient.totalElements;
              });
        }
      });
  }
  /** Return questions without images  */
  removeImagesFromQuestoions(listWithImage: any, listWithoutImage: any) {
    listWithImage.forEach(question => {
      const description = question.description;
      const re = /<\/p>|<p>/;
      const split = description.split(re);
      split.forEach(word => {
        if (word.includes('<img src=')) {
          const removeData = question.description.replace(word, '');
          question.description = removeData;
        }
      });
      listWithoutImage.push(question);
    });
    return listWithoutImage;
  }

  /** Update Quiz */
  updateQuiz() {
    if (this.listQuizQuestion.length === 0) {
      Swal({
        type: 'error',
        title: 'Oops...',
        text: 'Vous ne pouvez pas enregister ce QCM avec aucune question.',
      });
    } else {
      this.quiz.questions = this.listQuizQuestion;
      this.genericService.updateGeneric('/quizzes', this.quiz.id, this.quiz)
        .subscribe(
          data => {
            if (data.error === false) {
              Swal({
                position: 'top-end',
                type: 'success',
                title: data.value,
                showConfirmButton: false,
                timer: 1500
              });
              this.router.navigate(['administration/quizzes']);
            } else {
              Swal({
                title: 'Erreur!',
                text: data.value,
                type: 'error',
                confirmButtonText: 'ok'
              });
            }
          });
    }
  }

  /** Open Mark modal when assigning a question to a Quiz */
  openAddMarkModal(q: any) {
    this.listQuestionsWithImage.forEach(questionImage => {
      if (questionImage.id === q.id) {
        this.quizQuestion = new QuizQuestion();
        this.quizQuestion.question = questionImage;
      }
    });
  }


  /** Add queston to Quiz */
  addQuestion() {
    if ((this.listQuizQuestion.length === 0)) {
      this.listQuizQuestion = [];
      this.listQuizQuestion = this.listQuizQuestion.concat(this.quizQuestion);
      this.quizQuestion = new QuizQuestion();
    } else {
      for (const questionOfQuiz of this.listQuizQuestion) {
        if ((questionOfQuiz.question.id !== this.quizQuestion.question.id)) {
          this.exist = true;
        } else {
          Swal({
            title: 'Erreur!',
            text: 'Vous ne pouvez pas ajouter cette question, elle est déja existante dans le QCM!',
            type: 'error',
            confirmButtonText: 'Ok'
          });
          this.exist = false;
          break;
        }
      }
      if (this.exist) {
        this.listQuizQuestion = this.listQuizQuestion.concat(this.quizQuestion);
        this.quizQuestion = new QuizQuestion();
      }
    }
  }

  /** Open question detail modal */
  openDetailsModal(u: any) {
    this.listQuestionsWithImage.forEach(question => {
      if (question.id === u.id) {
        this.genericService.getGenericById('/questions', question.id).subscribe(data => {
          this.detailQuestion = data.value;
        });
      }
    });
  }

  /** Send quiz object to edit general information modal */
  openEditGeneralInfoModal(u: any) {
    this.quizToEdit = u;
  }

  /** Update general information of the quiz*/
  updateGeneralInfoQuiz() {
    this.quiz.title = this.quizToEdit.title;
    this.quiz.description = this.quizToEdit.description;
    this.quiz.duration = this.quizToEdit.duration;
  }

  /** Remove question from Quiz */
  removeAffectedQuestion(q: any) {
    Swal({
      title: 'Vous êtes Sur ?',
      text: 'Voulez vous vraiment enlever cette question',
      type: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Oui',
      cancelButtonText: 'Non'
    }).then((result) => {
      if (result.dismiss !== Swal.DismissReason.cancel && result.dismiss !== Swal.DismissReason.backdrop) {
        const index: number = this.listQuizQuestion.indexOf(q);
        if (index !== -1) {
          this.listQuizQuestion.splice(index, 1);
        }
      }
    });
  }

  /** Get random int */
  getRandomInt(min: number, max: number) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
  }

  /** Add random question to quiz */
  generateQuizz(n: number, m: number) {
    this.random = this.getRandomInt(0, (this.listQuestionsWithImage.length) - n);
    this.N = this.random + n;
    this.listOfGeneratedQuestions = this.listQuestionsWithImage.slice(this.random, this.N);
    this.listOfGeneratedQuestions.forEach(question => {
      this.quizQuestion = new QuizQuestion();
      this.quizQuestion.question = question;
      this.quizQuestion.mark = m;
      this.listQuizQuestion = this.listQuizQuestion.concat(this.quizQuestion);
    });

  }

  /** Create Question and add it to Quiz*/
  createQuestions() {
    this.genericService.getUserByUsername('/users/username', this.authenticationService.getUsername())
      .subscribe(data1 => {
        if (isNullOrUndefined(this.questionToAdd.description)) {
          Swal({
            title: 'Erreur!',
            text: 'Vous ne pouvez pas ajouter une question sans sa description',
            type: 'error',
            confirmButtonText: 'Ok'
          });
        } else if (this.questionToAdd.propositions.length < 2) {
          Swal({
            title: 'Erreur!',
            text: 'Vous ne pouvez pas ajouter moins que deux propositions',
            type: 'error',
            confirmButtonText: 'Ok'
          });
        } else {
          this.questionToAdd.createdBy = data1.value;
          this.questionToAdd.activity = data1.value.activity;
          this.questionToAdd.technology = this.quiz.technology;
          this.questionToAdd.degree = this.quiz.degree;
          this.quizQuestion.mark = this.mark;
          this.genericService.createGeneric('/questions', this.questionToAdd)
            .subscribe(data2 => {
              if (data2.error === false) {
                // Get list question with image for details and clone
                this.genericService.getGenericByTwoId('/questions/technology', '/degree', data2.value.technology.id, this.quiz.degree.id)
                  .subscribe(dataWithImg => {
                    this.listQuestionsWithImage = dataWithImg;
                    for (const question of this.listQuestionsWithImage) {
                      if ((question.id === data2.value.id)) {
                        this.questionWithImage = question;
                        break;
                      }
                    }
                    // Add created question to quiz list
                    this.quizQuestion.question = this.questionWithImage;
                    // this.addQuestion(this.quizQuestion);
                    this.addQuestion();
                    this.emptyObject();
                  });
                // get list of questions without image
                this.removeImagesFromQuestoions(this.listQuestionsWithImage, this.listQuestions);
                this.questionToAdd = new Question();
                this.proposition.description = null;
                Swal({
                  position: 'top-end',
                  type: 'success',
                  title: 'Question ajouté avec succés',
                  showConfirmButton: false,
                  timer: 1500
                });
                this.getQuestions();
              } else {
                Swal({
                  title: 'Erreur!',
                  text: data2.value,
                  type: 'error',
                  confirmButtonText: 'Ok'
                });
                this.emptyObject();
              }
            });
        }
      });
  }

  /** Create proposition in add question modal */
  addProposition(title, description, valid) {
    const proposition = {
      title: title,
      description: description,
      valid: valid
    };
    this.questionToAdd.propositions.push(proposition);
    this.proposition.description = null;
  }

  /** Edit proposition in add question modal */
  editPropositionAddQuestion(proposition) {
    proposition.editMode = false;
    this.p = new Proposition();
    this.p.description = proposition.description;
    this.p.title = proposition.title;
    this.p.valid = proposition.valid;
    const index = this.questionToAdd.propositions.indexOf(proposition);
    this.questionToAdd.propositions.splice(index, 1, this.p);
  }

  /** Delete proposition in add question modal */
  deletePropositionsAddQuestion(proposition) {
    this.questionToAdd.propositions.forEach((element, index) => {
      if (proposition.description === element.description) {
        this.questionToAdd.propositions.splice(index, 1);
      }
    });
  }

  /** Send question object to clone modal */
  openCloneModal(u: any) {
    this.listQuestionsWithImage.forEach(question => {
      if (question.id === u.id) {
        this.genericService.getGenericById('/questions', question.id).subscribe(data => {
          this.questionToClone = data.value;
        });
      }
    });
  }

  /** Clone Question */
  cloneQuestion(quizQuestionToClone: QuizQuestion) {
    this.questionToAdd = quizQuestionToClone.question;
    this.genericService.getUserByUsername('/users/username', this.authenticationService.getUsername())
      .subscribe(data1 => {
        this.questionToAdd.createdBy = data1.value;
        this.questionToAdd.activity = data1.value.activity;
        if (isNullOrUndefined(this.questionToAdd.description)) {
          Swal({
            title: 'Erreur!',
            text: 'Vous ne pouvez pas cloner une question sans ajouter une description',
            type: 'error',
            confirmButtonText: 'Ok'
          });
        } else if (this.questionToAdd.propositions.length < 2) {
          Swal({
            title: 'Erreur!',
            text: 'Vous ne pouvez pas ajouter moins que deux propositions',
            type: 'error',
            confirmButtonText: 'Ok'
          });
        } else {
          // this.questionToAdd.technology = this.quiz.technology;
          this.questionToAdd.degree = this.quiz.degree;
          this.genericService.createGeneric('/questions', this.questionToAdd)
            .subscribe(data2 => {
              // Get list question with image for details and clone
              this.genericService.getGenericByTwoId('/questions/technology', '/degree', data2.value.technology.id, this.quiz.degree.id)
                .subscribe(dataWithImg => {
                  this.listQuestionsWithImage = dataWithImg;
                  for (const question of this.listQuestionsWithImage) {
                    if ((question.id === data2.value.id)) {
                      this.questionWithImage = question;
                      break;
                    }
                  }
                  // Add created question to quiz list
                  this.quizQuestion.question = this.questionWithImage;
                  this.quizQuestion.mark = quizQuestionToClone.mark;
                  this.addQuestion();
                  this.emptyObject();
                  this.getQuestions();
                });
              // get list of questions without image
              this.removeImagesFromQuestoions(this.listQuestionsWithImage, this.listQuestions);
              this.questionToAdd = new Question();
              this.proposition.description = null;
              Swal({
                position: 'top-end',
                type: 'success',
                title: 'Question ajouté avec succés',
                showConfirmButton: false,
                timer: 1500
              });
            });
        }
      });
  }

  /** Edit mark of question in Quiz*/
  editMarkQuizQuestionToEdit(quizQuestion) {
    quizQuestion.editMode = false;
    this.quizQuestionToEdit = new QuizQuestion();
    this.quizQuestionToEdit.mark = quizQuestion.mark;
    this.quizQuestionToEdit.question = quizQuestion.question;
    this.quizQuestionToEdit.quiz = quizQuestion.quiz;
    this.quizQuestionToEdit.quizQuestionId = quizQuestion.quizQuestionId;
    const index = this.listQuizQuestion.indexOf(quizQuestion);
    this.listQuizQuestion.splice(index, 1, this.quizQuestionToEdit);
  }

  /** Empty add and clone question form fields */
  emptyObject() {
    this.questionToAdd = new Question();
    this.proposition.description = null;
    this.quizQuestion.mark = null;
  }

  /** Pagination: Change number of elements in the table */
  onSelect(pageNumber: number) {
    this.selectedPage = pageNumber - 1;
    if (this.searchInput !== '') {
      this.onSearch(this.searchInput);
    } else {
      this.getQuestions();
    }
  }

  /** Pagination: Change page number */
  getItems(itemsNumber: number) {
    this.item = itemsNumber;
    this.onSelect(1);
  }

  /** Simple search question */
  onSearch(search: String) {
    // Test the exist of SHOW_ALL_ACTIVITIE privilege
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
            // gets visible Questions of activity related to authenticated user
            this.genericService.searchGenericByAuthPageByTwoId('/questions/searchWithActivity',
              search, user.value.username, this.quiz.technology.id, this.quiz.degree.id, this.selectedPage, this.item)
              .subscribe(
                data => {
                  if (this.selectedPage > (data.value.totalPages) - 1) {
                    this.onSelect((data.value.totalPages));
                  } else {
                    this.listQuestionsWithImage = data.value.content;
                    this.listQuestions = [];
                    // get list of questions without image
                    this.removeImagesFromQuestoions(this.listQuestionsWithImage, this.listQuestions);
                    this.pageClient = new PageClient();
                    this.pageClient = data.value;
                    this.total = this.pageClient.totalElements;
                  }
                });
          } else {
            // gets all visible questions without activity criteria
            this.genericService.searchGenericByAuthPageByTwoId('/questions/searchWithoutActivity', search, user.value.username,
              this.quiz.technology.id, this.quiz.degree.id, this.selectedPage, this.item)
              .subscribe(
                data => {
                  if (this.selectedPage > (data.totalPages) - 1) {
                    this.onSelect((data.totalPages));
                  } else {
                    this.listQuestionsWithImage = data.content;
                    this.listQuestions = [];
                    // get list of questions without image
                    this.removeImagesFromQuestoions(this.listQuestionsWithImage, this.listQuestions);
                    this.pageClient = new PageClient();
                    this.pageClient = data;
                    this.total = this.pageClient.totalElements;
                  }
                });
          }
        });
    } else {
      this.getQuestions();
    }
  }

  /*Get all Technologies for select form*/
  getTechnologies() {
    this.genericService.getGenericList('/technologies/all')
      .subscribe(
        data => {
          this.listTechnologies = data;
        });
  }

  /**Check box action for visibility of the question when creating a new one */
  checkVisibility() {
    if (this.visibilityValue) {
      this.questionToAdd.shared = true;
    } else {
      this.questionToAdd.shared = false;
    }
  }

}
