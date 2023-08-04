import { Session } from './session.model';
import { User } from './user.model';

export class Application {
  id!: number;
  comment!: string;
  cv!: string;
  date!: Date;
  diplome!: string;
  experience!: number;
  itKnownledge!: boolean;
  mativation!: string;
  situation!: string;
  specialite!: string;
  status!: string;
  session!: Session;
  user!: User;
}
