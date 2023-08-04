import { Cursus } from './cursus.model';

export class Session {
  id!: number;
  name!: string;
  startDate!: Date;
  endDate!: Date;
  status!: string;
  cursus!: Cursus;
}
