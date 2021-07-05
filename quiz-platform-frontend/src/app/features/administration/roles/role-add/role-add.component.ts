import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { GenericService } from '@services/generic.service';
import { Role } from '@models/role';
import swal from 'sweetalert2';
import { isNullOrUndefined } from 'util';
import { Privilege } from '@models/privilege';

@Component({
  selector: 'app-role-add',
  templateUrl: './role-add.component.html',
  styleUrls: ['./role-add.component.css']
})
export class RoleAddComponent implements OnInit {

  // Role object for create action
  roleToAdd: Role = new Role();

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
  selectedPrivilegesAspects: Privilege[] = [];
  selectedPrivilegesDegrees: Privilege[] = [];
  selectedPrivilegesQuestions: Privilege[] = [];
  selectedPrivilegesQuizzes: Privilege[] = [];
  selectedPrivilegesInterviews: Privilege[] = [];
  selectedPrivilegesMessages: Privilege[] = [];
  selectedPrivilegesAffectationQuiz: Privilege[] = [];

  // List of titles of roles to check the existance of any role with the same title
  listTitlesRoles: any = [];
  existingRole: string;
  errorRole = false;
  // Access to all activites values
  activitiesValue: boolean;
  activitiesAccess = false;
  // Event for reload table content
  @Output() reloadEvent = new EventEmitter();

  constructor(private roleService: GenericService) { }

  ngOnInit() {
    this.getTitlesOfRoles();
    this.getAllPrivileges();
  }


  /** Get all titles of roles to control the unicity of title's of role */
  getTitlesOfRoles() {
    this.roleService.getGenericList('/roles/titles').subscribe(
      data => {
        this.listTitlesRoles = data;
      }
    );
  }

  /** Compare existing title of roles with tapped title of role */
  onSearchChangeRole(searchValue: string) {
    function findRole(title) {
      return title === searchValue;
    }
    this.existingRole = this.listTitlesRoles.find(findRole);
    if (isNullOrUndefined(this.existingRole)) {
      this.errorRole = false;
    } else {
      this.errorRole = true;
    }
  }

  /** Create Role */
  createRole() {
    // test if role to add can access to all activities or not
    if (this.activitiesAccess === true) {
      this.roleToAdd.privileges.concat(this.PrivilegesAllActivities);
    } else {
      const index = this.roleToAdd.privileges.indexOf(this.PrivilegesAllActivities);
      this.roleToAdd.privileges.splice(index, 1);
    }
    this.roleToAdd.privileges = this.roleToAdd.privileges.concat(this.PrivilegesAllActivities).concat(this.selectedPrivilegesAspects).
      concat(this.selectedPrivilegesDegrees).concat(this.selectedPrivilegesQuizzes).concat(this.selectedPrivilegesQuestions).
      concat(this.selectedPrivilegesInterviews).concat(this.selectedPrivilegesAffectationQuiz).concat(this.selectedPrivilegesMessages);
    this.roleService.createGeneric('/roles', this.roleToAdd)
      .subscribe(data => {
        if (data.error === false) {
          swal({
            position: 'top-end',
            type: 'success',
            title: data.value,
            showConfirmButton: false,
            timer: 1500
          });
          // reload table data
          this.reloadEvent.emit(null);
          // empty add form fields
          this.emptyObject();
        } else {
          swal({
            title: 'Erreur!',
            text: data.value,
            type: 'error',
            confirmButtonText: 'Ok'
          });
          this.emptyObject();
        }
      }, err => { console.log(err); });
  }


  /** Get all privileges of roles to display according to a category */
  getAllPrivileges() {
    this.roleService.getGenericList('/privileges').subscribe(
      data => {
        // Get all privileges
        this.listAllPrivileges = data;
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
          // PUT privileges with category MESSAGE in PrivilegesForDegree list
          if (privilege.category === 'AFFECTATION_QUIZ') {
            this.PrivilegesForAffectationQuiz.push(privilege);
          }
          // PUT privileges with category ALL_ACTIVITIES in PrivilegesAllActivities list
          if (privilege.category === 'ALL_ACTIVITIES') {
            this.PrivilegesAllActivities = privilege;
          }
        });
        this.displayedAspects = this.PrivilegesForAspect;
        this.displayedQuizzes = this.PrivilegesForQuiz;
        this.displayedQuestions = this.PrivilegesForQuestion;
        this.displayedDegrees = this.PrivilegesForDegree;
        this.displayedInterviews = this.PrivilegesForInterview;
        this.displayedMessages = this.PrivilegesForMessage;
        this.displayedAffectationQuiz = this.PrivilegesForAffectationQuiz;
      }
    );
  }

  /** Empty add form fields */
  emptyObject() {
    this.roleToAdd = new Role();
    // Empty SelectedPrivileges
    this.selectedPrivilegesAspects = [];
    this.selectedPrivilegesDegrees = [];
    this.selectedPrivilegesQuestions = [];
    this.selectedPrivilegesQuizzes = [];
    this.selectedPrivilegesInterviews = [];
    this.selectedPrivilegesMessages = [];
    this.selectedPrivilegesAffectationQuiz = [];
  }

  /** Check if all activities is shared for role user or not (Check box value) */
  checkActivities() {
    if (this.activitiesValue) {
      this.activitiesAccess = true;
    } else {
      this.activitiesAccess = false;
    }
  }

}
