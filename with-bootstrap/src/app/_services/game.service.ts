import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class GameService {

  constructor(private http: HttpClient) { }

  getGames(page: number = 0, size: number = 40) {
    return this.http.get<any>(`http://localhost:8082/api/v1/games?page=${page}&size=${size}`);
  }

  getGameById(id: number) {
    return this.http.get<any>(`http://localhost:8082/api/v1/games/${id}`);
  }
}
