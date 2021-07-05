import { User } from "./user";
import { AssignedQuizUserId } from "./assigned-quiz-user-id";

export class AssignedQuizUser {

    id: AssignedQuizUserId = new AssignedQuizUserId();
    assignedAt: Date;
    assignedBy: User = new User();
    passingAt: Date;

    constructor() { }
}
