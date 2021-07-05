import { AssignedQuizOnlineInterviewId } from './assigned-quiz-online-interview-id';
import { Quiz } from './quiz';

export class AssignedQuizOnlineInterview {
    id:AssignedQuizOnlineInterviewId = new AssignedQuizOnlineInterviewId();
    assignedAt:Date;
    passingAt:Date;
    quiz:Quiz = new Quiz();
}
