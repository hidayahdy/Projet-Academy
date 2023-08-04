import { Lesson } from './lesson.model';

export class Synopsis {
  id!: number;
  title!: string;
  description!: string;
  goals!: string;
  lesson!: Lesson;
}
