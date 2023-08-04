import { Lesson } from './lesson.model';

export class Resource {
  id!: number;
  name!: string;
  link!: string;
  like!: number;
  lesson!: Lesson;
}
