import { QuizQuestionId } from './quiz-question-id';
import { Quiz } from './quiz';
import { Question } from './question';

export class QuizQuestion {
    id: QuizQuestionId = new QuizQuestionId();
    quiz:Quiz = new Quiz();
    question:Question = new Question();
    mark:number;

    constructor(){}


}
