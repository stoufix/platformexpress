import { Quiz } from './quiz';
import { CandidateQuizId } from './candidate-quiz-id';

export class CandidateQuizResult {

    id: CandidateQuizId = new CandidateQuizId();
    percent: any;
    passingDate: Date;
    quiz: Quiz = new Quiz();

    constructor() { }
}
