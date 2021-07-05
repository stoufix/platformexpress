import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Role } from '@models/role';
import { GenericService } from '@services/generic.service';
import Swal from 'sweetalert2';
import { Privilege } from '@models/privilege';
import { isNullOrUndefined } from 'util';

@Component({
  selector: 'app-role-update',
  templateUrl: './role-update.component.html',
  styleUrls: ['./role-update.component.css']
})
export class RoleUpdateComponent implements OnInit {

  // Get Role to edit form roles list component
  @Input() roleToEdit: Role;

  // Event for reload table content after update
  @Output() reloadEvent = new EventEmitter();

  // List of privileges by category
  listAllPrivileges: any;
  PrivilegesForAspect: any = [];
  PrivilegesForQuiz: any = [];
  PrivilegesForQuestion: any = [];
  PrivilegesForDegree: any = [];
  PrivilegesForInterview: any = [];
  PrivilegesForMessage: any = [];
  PrivilegesForAffectationQuiz: any = [];
  PrivilegesAllActivities: any;

  // List for displaying priveleges by category
  displayedAspects: any = [];
  displayedQuizzes: any = [];
  displayedQuestions: any = [];
  displayedDegrees: any = [];
  displayedInterviews: any = [];
  displayedMessages: any = [];
  displayedAffectationQuiz: any = [];

  // List of selected privileges by category(for create role)
  selectedPrivilegesAspectsToEdit: Privilege[] = [];
  selectedPrivilegesDegreesToEdit: Privilege[] = [];
  selectedPrivilegesQuestionsToEdit: Privilege[] = [];
  selectedPrivilegesQuizzesToEdit: Privilege[] = [];
  selectedPrivilegesInterviewsToEdit: Privilege[] = [];
  selectedPrivilegesMessagesToEdit: Privilege[] = [];
  selectedPrivilegesAffectationQuizToEdit: Privilege[] = [];

  // List of titles of roles to check the existance of any role with the same title
  listTitlesRoles: any = [];
  existingRole: string;
  errorRole = false;
  // Access to all activites values
  activitiesValue: boolean;
  activitiesAccess = false;

  constructor(private genericService: GenericService) { }

  ngOnInit() {
    this.listPrivileges();
  }


  /** Compare existing title of roles with tapped title of role */
  onSearchChangeRole(searchValue: string) {
    function findRole(title) {
      return title === searchValue;
    }
    this.genericService.getGenericById('/roles', this.roleToEdit.id).subscribe(
      role => {
        const index = this.listTitlesRoles.indexOf(role.value.title);
        this.listTitlesRoles.splice(index, 1);
      });
    this.existingRole = this.listTitlesRoles.find(findRole);
    if (isNullOrUndefined(this.existingRole)) {
      this.errorRole = false;
    } else {
      this.errorRole = true;
    }
  }

  /** Update Role */
  updateRole() {
    this.errorRole = false;
    this.roleToEdit.privileges = [];
    // test if role to edit can access to all activities or not
    if (this.activitiesAccess === true) {
      this.roleToEdit.privileges = this.roleToEdit.privileges.concat(this.PrivilegesAllActivities);
    } else {
      const index = this.roleToEdit.privileges.indexOf(this.PrivilegesAllActivities);
      this.roleToEdit.privileges.splice(index, 1);
    }

    this.roleToEdit.privileges = this.roleToEdit.privileges.concat(this.selectedPrivilegesAspectsToEdit).
      concat(this.selectedPrivilegesDegreesToEdit).concat(this.selectedPrivilegesQuizzesToEdit).
      concat(this.selectedPrivilegesQuestionsToEdit).concat(this.selectedPrivilegesInterviewsToEdit).
      concat(this.selectedPrivilegesAffectationQuizToEdit).concat(this.selectedPrivilegesMessagesToEdit);
    this.genericService.updateGeneric('/roles', this.roleToEdit.id, this.roleToEdit).subscribe(
      data => {
        if (data.error === false) {
          Swal({
            position: 'top-end',
            type: 'success',
            title: data.value,
            showConfirmButton: false,
            timer: 1500
          });
          // reload table data
          this.reloadEvent.emit(null);
        } else {
          Swal({
            title: 'Erreur!',
            text: data.value,
            type: 'error',
            confirmButtonText: 'ok'
          });
        }

      },
      error => console.log('Role update Error: ' + error));
  }

  /** Get all privileges of roles to display according to a category */
  listPrivileges() {
    this.genericService.getGenericList('/roles/titles').subscribe(
      data => {
        this.listTitlesRoles = data;
        this.genericService.getGenericList('/privileges').subscribe(
          data1 => {
            // Get all privileges
            this.listAllPrivileges = data1;
            this.listAllPrivileges.forEach(privilege => {
              // PUT privileges with category ASPECT in PrivilegesForAspect list
              if (privilege.category === 'TECHNOLOGY') {
                this.PrivilegesForAspect.push(privilege);
              }
              // PUT privileges with category QUIZ in PrivilegesForQuiz list
              if (privilege.category === 'QUIZ') {
                this.PrivilegesForQuiz.push(privilege);
              }
              // PUT privileges with category QUESTION in PrivilegesForQuestion list
              if (privilege.category === 'QUESTION') {
                this.PrivilegesForQuestion.push(privilege);
              }
              // PUT privileges with category DEGREE in PrivilegesForDegree list
              if (privilege.category === 'DEGREE') {
                this.PrivilegesForDegree.push(privilege);
              }
              // PUT privileges with category INTERVIEW in PrivilegesForInterview list
              if (privilege.category === 'INTERVIEW') {
                this.PrivilegesForInterview.push(privilege);
              }
              // PUT privileges with category MESSAGE in PrivilegesForDegree list
              if (privilege.category === 'RECLAMATION') {
                this.PrivilegesForMessage.push(privilege);
              }
              // PUT privileges with category ALL_ACTIVITIES in PrivilegesAllActivities list
              if (privilege.category === 'ALL_ACTIVITIES') {
                this.PrivilegesAllActivities = privilege;
              }
              // PUT privileges with category MESSAGE in PrivilegesForDegree list
              if (privilege.category === 'AFFECTATION_QUIZ') {
                this.PrivilegesForAffectationQuiz.push(privilege);
              }
              this.displayedAspects = this.PrivilegesForAspect;
              this.displayedQuizzes = this.PrivilegesForQuiz;
              this.displayedQuestions = this.PrivilegesForQuestion;
              this.displayedDegrees = this.PrivilegesForDegree;
              this.displayedInterviews = this.PrivilegesForInterview;
              this.displayedMessages = this.PrivilegesForMessage;
              this.displayedAffectationQuiz = this.PrivilegesForAffectationQuiz;
            });
            this.roleToEdit.privileges.forEach(privilege => {
              // List of selected privileges by category(for update role)
              if (privilege.category === 'TECHNOLOGY') {
                this.selectedPrivilegesAspectsToEdit.push(privilege);
              }
              if (privilege.category === 'DEGREE') {
                this.selectedPrivilegesDegreesToEdit.push(privilege);
              }
              if (privilege.category === 'QUESTION') {
                this.selectedPrivilegesQuestionsToEdit.push(privilege);
              }
              if (privilege.category === 'QUIZ') {
                this.selectedPrivilegesQuizzesToEdit.push(privilege);
              }
              if (privilege.category === 'INTERVIEW') {
                this.selectedPrivilegesInterviewsToEdit.push(privilege);
              }
              if (privilege.category === 'RECLAMATION') {
                this.selectedPrivilegesMessagesToEdit.push(privilege);
              }
              if (privilege.category === 'AFFECTATION_QUIZ') {
                this.selectedPrivilegesAffectationQuizToEdit.push(privilege);
              }
              if (privilege.category === 'ALL_ACTIVITIES') {
                this.activitiesValue = true;
              }
            });
          });
      });
  }
  /** Check if all activities is shared for role to edit or not (Check box value) */
  checkActivities() {
    if (this.activitiesValue) {
      this.activitiesAccess = true;
    } else {
      this.activitiesAccess = false;
    }
  }

}
