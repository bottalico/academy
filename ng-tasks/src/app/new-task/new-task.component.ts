import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Task } from '../shared/model/task';

@Component({
  selector: 'app-new-task',
  templateUrl: './new-task.component.html',
  styleUrls: ['./new-task.component.css'],
})
export class NewTaskComponent implements OnInit {
  text: string = '';
  @Output() newTask = new EventEmitter<Task>();

  constructor() {}

  ngOnInit(): void {}

  add() {
    if (this.text.trim().length > 0) {
      this.newTask.emit({ text: this.text.trim(), completed: false });
      this.text = '';
    }
  }
}
