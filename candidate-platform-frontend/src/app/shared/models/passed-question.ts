import { OnlineInterviewQuizResult } from './online-interview-quiz-result';
import { AnsweredProposition } from './answered-proposition';
export class PassedQuestion {

    id: number;
    description: string;
    // onlineInterviewQuizResult:OnlineInterviewQuizResult = new OnlineInterviewQuizResult();
    answeredProposition: Array<AnsweredProposition> = [];

    constructor() { }
}
