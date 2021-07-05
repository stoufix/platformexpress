import { User } from './user';
import { Degree } from './degree';
import { Technology } from './technology';
import { QuizQuestion } from './quiz-question';
import { Activity } from './activity';

export class Quiz {

    id: number;
    title: String;
    description: String;
    duration: number;
    createdAt: Date;
    updatedAt: Date;
    createdBy: User = new User();
    degree: Degree = new Degree();
    questions: Array<QuizQuestion>;
    technology: Technology = new Technology();
    activity: Activity = new Activity();
    shared = false;

    constructor() { }

}
