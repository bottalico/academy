import {
  AfterViewInit,
  Component,
  ElementRef,
  Input,
  OnInit,
  ViewChild,
} from '@angular/core';
import {
  NumberGuessingGame,
  Result,
} from '../shared/model/number-guessing-game';

@Component({
  selector: 'app-number-guessing-game',
  templateUrl: './number-guessing-game.component.html',
  styleUrls: ['./number-guessing-game.component.css'],
})
export class NumberGuessingGameComponent implements OnInit, AfterViewInit {
  @Input() min: number = 100;
  @Input() max: number = 200;

  game?: NumberGuessingGame;

  @ViewChild('inv') inputField!: ElementRef;

  constructor() {}

  ngOnInit(): void {
    this.game = this.newGame();
  }

  ngAfterViewInit() {
    this.inputField.nativeElement.focus();
  }

  tryNumber(e: SubmitEvent) {
    e.preventDefault();
    let guess: number = Number(this.inputField.nativeElement.value);
    let result: Result = {
      number: guess,
      comparaison: guess - this.game!.number,
    };
    this.game?.results.push(result);
    this.game!.won = result.comparaison === 0;

    this.inputField.nativeElement.value = '';
    this.inputField.nativeElement.focus();
  }

  messageFor(r: Result): string {
    if (r.comparaison === 0) {
      return 'Bravo, hai indovinato';
    } else if (r.comparaison < 0) {
      return 'Troppo piccolo';
    } else {
      return 'Troppo grande';
    }
  }

  resultClasses(r: Result): any {
    return {
      'list-group-item': true,
      'list-group-item-success': r.comparaison == 0,
      'list-group-item-primary': r.comparaison < 0,
      'list-group-item-danger': r.comparaison > 0,
    };
  }

  resetGame(): void {
    this.game = this.newGame();
  }

  private newGame(): NumberGuessingGame {
    return {
      number: Math.ceil(Math.random() * (this.max - this.min + 1) + this.min),
      results: [],
      won: false,
    };
  }
}
