import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit {
  constructor() { }
  public isLogIn = false;
  public isAdmin = false;
  ngOnInit(): void {
  }

  logedIn() {
    this.isLogIn = true;
  }

  adminLogIn(){
    this.isAdmin=true;
    this.isLogIn=true;
  }

}
