import { Quiz } from './quiz';
import { PassedQuestion } from './passed-question';
import { OnlineInterview } from './online-interview';
import { OnlineInterviewQuizResultId } from './online-interview-quiz-result-id';

export class OnlineInterviewQuizResult {

    id: OnlineInterviewQuizResultId = new OnlineInterviewQuizResultId();
    note: number;
    interviewDate: Date;
    duration: number;
    comments: String;
    passedQuestions: Array<PassedQuestion> = [];
    onlineInterview: OnlineInterview = new OnlineInterview();
    quiz: Quiz = new Quiz();

    constructor() { }
}
