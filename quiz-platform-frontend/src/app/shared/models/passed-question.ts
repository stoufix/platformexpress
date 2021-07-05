import { AnsweredProposition } from './answered-proposition';

export class PassedQuestion {

    id: number;
    description: string;
    answeredProposition: Array<AnsweredProposition> = [];

    constructor() { }
}
