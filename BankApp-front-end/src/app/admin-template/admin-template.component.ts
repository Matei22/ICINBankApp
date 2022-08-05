import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-template',
  templateUrl: './admin-template.component.html',
  styleUrls: ['./admin-template.component.css']
})
export class AdminTemplateComponent implements OnInit {

  constructor(private router:Router) { }

  ngOnInit(): void {
  }
  handleLogout(){
    this.router.navigateByUrl('/login')

  }

}
