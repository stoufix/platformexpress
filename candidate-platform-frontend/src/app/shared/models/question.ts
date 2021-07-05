import { User } from './user';
import { QuizQuestion } from './quiz-question';
import { Proposition } from './proposition';
import { Technology } from './technology';
import { Degree } from './degree';
import { Activity } from './activity';

export class Question {
    id:number;
    description:string;
    createdAt: Date;
    updatedAt: Date;
    shared:Boolean;
    createdBy:User = new User();
    //quizList:QuizQuestion = new QuizQuestion();
    propositions: Array<Proposition>=[];
    technology: Technology = new Technology();
    degree: Degree = new Degree();
    activity: Activity = new Activity();
    
    constructor() {}
}
