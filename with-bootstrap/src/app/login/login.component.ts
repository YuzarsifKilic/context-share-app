import { Component } from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  form = new FormGroup({
    email: new FormControl("", [Validators.required, Validators.email]),
    password: new FormControl("", Validators.required)
  })

  get formControls() {
    return this.form.controls;
  }

  login() {
    if (this.form.valid) {
      console.log(this.form.value);
    } else {
      this.displayErrors();
    }
  }

  displayErrors() {
    Object.keys(this.form.controls).forEach(key => {
      const controlErrors = this.form.get(key)!.errors;
      if (controlErrors) {
        Object.keys(controlErrors).forEach(keyError => {
          console.log('Key control: ' + key + ', keyError: ' + keyError + ', error value: ', controlErrors[keyError]);
        });
      }
    });
  }
}
