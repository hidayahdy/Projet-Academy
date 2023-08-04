import { Session } from "./session.model";

export class User {
  id!: number;
  email!: string;
  firstName!: string;
  lastName!: string;
  password!: string;
  phone!: number;
  adress!: string;
  picture!: string;
  linkedin!: string;
  session !: Session
}
