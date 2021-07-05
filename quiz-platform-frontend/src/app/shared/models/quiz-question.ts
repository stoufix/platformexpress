import { Quiz } from './quiz';
import { Question } from './question';
import { QuizQuestionId } from './quiz-question-id';

export class QuizQuestion {

    quizQuestionId: QuizQuestionId = new QuizQuestionId;
    quiz: Quiz = new Quiz();
    question: Question = new Question();
    mark: number;

    constructor() { }

}
