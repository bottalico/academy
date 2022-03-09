import { Injectable } from '@angular/core';
import { Task } from '../model/task';

@Injectable({
  providedIn: 'root',
})
export class TaskService {
  private tasks: Task[] = [
    { text: 'Lavati i denti', completed: false },
    { text: 'Spiega come scrivere i componenti', completed: true },
    { text: 'Ricordati che la pausa è alle 13', completed: false },
  ];

  constructor() {}

  getTasks(): Task[] {
    return this.tasks;
  }

  getFilteredTasks(filter: string) {
    return this.tasks.filter((t) => t.text.includes(filter));
  }

  add(task: Task) {
    this.tasks.push(task);
  }

  hasNotHiddenTasks(): boolean {
    return this.tasks.length !== 0 && !this.tasks.every((t) => t.hidden);
  }
}
