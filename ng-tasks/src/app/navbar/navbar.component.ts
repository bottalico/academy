import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
  @Input() navTitle: string = 'none';

  constructor(private router: Router) {}

  goToSearch(q: string) {
    console.log(`Received query ${q}`);
    this.router.navigate(['/tasks'], { queryParams: { q } });
  }

  ngOnInit(): void {}
}
