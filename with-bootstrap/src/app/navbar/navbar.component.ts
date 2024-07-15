import { Component } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {

  constructor(private router: Router) { }

  gamePage() {
    this.router.navigate(['/game']);
  }

  loginPage() {
    this.router.navigate(['/login']);
  }

  moviePage() {
    this.router.navigate(['/movie']);
  }
}
