import { Question } from './question';

export class Proposition {
    id:number;
    description:string;
    valid:boolean;
    checked:boolean= false;
    question:Question = new Question();

    constructor(){}
}
