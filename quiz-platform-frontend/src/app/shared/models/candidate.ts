import { Application } from './application';

export class Candidate {

    id: number;
    firstName: String;
    lastName: String;
    email: String;
    address: String;
    phoneNumber: number;
    birthDate: any;
    graduationYear: any;
    university: String;
    experience: number;
    availabilityDate: any;
    username: String;
    password: String;
    profil: String;
    status: String;
    activated: Boolean;
    comments: String;
    // WARNING in Circular dependency detected
    applications: Array<Application> = [];

    constructor() { }
}
