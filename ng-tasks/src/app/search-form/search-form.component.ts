import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-search-form',
  templateUrl: './search-form.component.html',
  styleUrls: ['./search-form.component.css'],
})
export class SearchFormComponent implements OnInit {
  formclass = true;
  buttonClasses = {
    btn: true,
    'btn-outline-success': true,
  };

  counter: number = 0;

  @Output() newValue = new EventEmitter<string>();

  constructor() {}

  searchSubmitted(f: NgForm) {
    console.log(`Sumitted ${f.value.currentSearchValue}`);
    this.newValue.emit(f.value.currentSearchValue);
  }

  ngOnInit(): void {}
}
