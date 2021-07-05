import { User } from './user';
import { Technology } from './technology';
import { Degree } from './degree';
import { Proposition } from './proposition';
import { Activity } from './activity';

export class Question {

    id: number;
    description: string;
    createdAt: Date;
    updatedAt: Date;
    createdBy: User = new User();
    propositions: Array<Proposition> = [];
    technology: Technology = new Technology();
    degree: Degree = new Degree();
    activity: Activity = new Activity();
    shared = false;

    constructor() { }

}
