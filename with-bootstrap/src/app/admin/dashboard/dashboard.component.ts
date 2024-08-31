import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

  @Input() title: string = "";
  @Input() content: string = "";
  @Input() footer: string = "";

}
