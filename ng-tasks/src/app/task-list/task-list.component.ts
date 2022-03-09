import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Task } from '../shared/model/task';
import { TaskService } from '../shared/service/task.service';

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css'],
})
export class TaskListComponent implements OnInit {
  constructor(
    private route: ActivatedRoute,
    private taskService: TaskService
  ) {}

  ngOnInit(): void {}

  filteredTasks(): Task[] {
    if (this.route.snapshot.queryParamMap.has('q')) {
      const q = this.route.snapshot.queryParamMap.get('q');
      return this.taskService.getFilteredTasks(q!);
    }
    return this.taskService.getTasks();
  }

  canShowTasks(): boolean {
    return this.taskService.hasNotHiddenTasks();
  }

  addTask(task: Task): void {
    this.taskService.add(task);
  }

  toggleTask(task: Task) {
    task.completed = !task.completed;
  }

  hideTask(task: Task) {
    task.hidden = true;
  }
}
