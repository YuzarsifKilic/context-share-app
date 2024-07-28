import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {MatTab, MatTabContent, MatTabGroup} from "@angular/material/tabs";
import {GameHomeComponent} from "../game/game-home/game-home.component";
import {MovieHomeComponent} from "../movie/movie-home/movie-home.component";

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    MatTabGroup,
    MatTab,
    MatTabContent,
    GameHomeComponent,
    MovieHomeComponent
  ],
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
