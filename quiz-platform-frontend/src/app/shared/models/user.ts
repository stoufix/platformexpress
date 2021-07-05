import { Role } from './role';
import { Activity } from './activity';
import { AssignedQuizOnlineInterview } from './assigned-quiz-online-interview';

export class User {

    id: number;
    firstName: String;
    lastName: String;
    email: String;
    address: String;
    phoneNumber: any;
    birthDate: any;
    username: string;
    password: string;
    university: string;
    level: string;
    yearOfGraduation: string;
    role: Role = new Role();
    activity: Activity = new Activity();
    status: string;
    activated: any;
    assignedQuizzes: Array<AssignedQuizOnlineInterview> = [];

    constructor() { }

}
