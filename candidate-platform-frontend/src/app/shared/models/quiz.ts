import { User } from './user';
import { QuizResult } from './quiz-result';
import { AssignedQuizOnlineInterview } from './assigned-quiz-online-interview';
import { Degree } from './degree';
import { Question } from './question';
import { Technology } from './technology';
import { Activity } from './activity';
import { QuizQuestion } from './quiz-question';

export class Quiz {
    id:number;
    title:string;
    description:string;
    duration:number;
    createdAt:Date;
    updatedAt:Date;
    shared:Boolean;
    createdBy:User = new User();
    passedBy: QuizResult = new QuizResult();
   // assignedInterviews: AssignedQuizOnlineInterview = new AssignedQuizOnlineInterview();
    degree: Degree = new Degree();
    questions: Array<QuizQuestion>;
    technology: Technology = new Technology();
    activity: Activity = new Activity();

    constructor(){}
}
