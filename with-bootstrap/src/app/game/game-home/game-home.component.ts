import {Component} from '@angular/core';
import {NgForOf} from "@angular/common";
import {GameService} from "../../_services/game.service";
import {MatCard, MatCardContent, MatCardHeader, MatCardModule} from "@angular/material/card";
import {MatButtonModule} from "@angular/material/button";
import {Router} from "@angular/router";

@Component({
  selector: 'app-game-home',
  standalone: true,
  imports: [
    MatCardModule,
    MatButtonModule,
    NgForOf
  ],
  templateUrl: './game-home.component.html',
  styleUrl: './game-home.component.css'
})
export class GameHomeComponent {

  games: any[] = [];

  constructor(private gameService: GameService, private router: Router) { }

  ngOnInit() {
    this.gameService.getGames(undefined, undefined).subscribe(resp => {
      this.games = resp.content;
    })
  }


  gameDetail(id: number) {
    this.router.navigate(["/game-details/", id]);
  }
}
