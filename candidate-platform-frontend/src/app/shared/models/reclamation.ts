import { Candidate } from './candidate';

export class Reclamation {
    id: number;
    subject: string;
    body: string;
    consulted: Boolean;
    createdAt: Date;
    candidate:Candidate = new Candidate();
    selected :Boolean = false;

    constructor() {}
}
