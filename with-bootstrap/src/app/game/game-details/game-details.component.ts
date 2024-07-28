import { Component } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {GameService} from "../../_services/game.service";
import {NgForOf, NgIf} from "@angular/common";
import {CommentsComponent} from "../../comments/comments.component";

@Component({
  selector: 'app-game-details',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    CommentsComponent
  ],
  templateUrl: './game-details.component.html',
  styleUrl: './game-details.component.css'
})
export class GameDetailsComponent {

  gameId?: number;
  game: any;
  displayedColumns: string[] = ['Store', 'Platform', 'Price', 'Discount Price', 'Discount Start Date', 'Discount End Date', 'Last Price'];

  constructor(private route: ActivatedRoute, private gameService: GameService) { }

  ngOnInit() {
    this.gameId = Number(this.route.snapshot.paramMap.get('id'));
    this.gameService.getGameById(this.gameId).subscribe(resp => {
      this.game = resp;
    })
  }

  getLanguages(audioLanguages: any) {
    let languages = audioLanguages.map((language: any) => {
      return language.name;
    })
    return languages.join(", ");
  }

  getGraphicsCards() {
    let graphicsCards = this.game.minSystemRequirement.graphics.map((graphicsCard: any) => {
      return graphicsCard.brand + " " + graphicsCard.version;
    })
    return graphicsCards.join(" - ");
  }

  showStore(url: string) {
    window.open(url, '_blank');
  }
}
