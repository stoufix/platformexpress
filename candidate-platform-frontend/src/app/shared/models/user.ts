import { Role } from './role';
import { Activity } from './activity';
import { Quiz } from './quiz';
import { Question } from './question';

export class User {
    id:number;
    firstName:string;
    lastName:string;
    email:string;
    address:string;
    phoneNumber:number;
    birthDate:Date;
    username:string;
    password:string;
    activated:boolean;
    role: Role = new Role();
    activity: Activity = new Activity();
   // createdQuizzes:Quiz = new Quiz();
   // createdQuestions: Question = new Question();

    constructor(){}
}
