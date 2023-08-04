import { Lesson } from './lesson.model';

export class Module {
  id!: number;
  name!: string;
  description!: string;
  lessonDto!: [Lesson];
}
