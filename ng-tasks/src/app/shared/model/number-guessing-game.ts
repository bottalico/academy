export interface NumberGuessingGame {
  number: number;
  results: Result[];
  won: boolean;
}

export interface Result {
  number: number;
  comparaison: number;
}
