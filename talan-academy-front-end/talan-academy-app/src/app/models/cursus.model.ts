import { Module } from './module.model';

export interface Cursus {
  id?: number;
  name: string;
  picture: string;
  description: string;
  keyWords: string[];
  moduleDto: [Module];
}
