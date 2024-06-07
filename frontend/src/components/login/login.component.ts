import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
var sha256 = require("crypto-js/sha256");

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
    username: string = '';
    password: string = '';

    constructor(private http: HttpClient, private router: Router) {}

    login() {

        const spinnerElement = document.querySelector(".mat-spinner") as HTMLElement;
        const loginFormElements = document.querySelector(".loginFormElements") as HTMLElement;
        const errorMessageElement = document.querySelector(".errorMessage") as HTMLElement;
        loginFormElements.style.display = "none";
        errorMessageElement.style.display = "none";
        spinnerElement.style.display = "block";

        const loginData = {
          login: this.username,
          password: sha256(this.password).toString(),
        };
    
        const headers = new HttpHeaders({
            'Content-Type': 'application/x-www-form-urlencoded'
        });

        const formData = this.serializeFormData(loginData);

        this.http.post<any>('http://localhost:8080/login', formData, { headers }).subscribe(
          (response: any) => {
            
            spinnerElement.style.display = "none";
            loginFormElements.style.display = "flex";

            if (response[0] == "") {
                errorMessageElement.style.display = "inline";
            } else {
                localStorage.setItem('Authorization', response[0]);
                this.router.navigate(["admin-panel"]);
            }
          },
          (error: any) => {
            console.error(error);
          }
        );
    }

    private serializeFormData(data: any) {
        let encodedString = '';
        for (const key in data) {
          if (encodedString.length !== 0) {
            encodedString += '&';
          }
          encodedString += `${encodeURIComponent(key)}=${encodeURIComponent(data[key])}`;
        }
        return encodedString;
      }
    

    ngOnInit(): void {
        const authorization = localStorage.getItem('Authorization');

        if (authorization) {

            const headers = new HttpHeaders({
                'Authorization': authorization
            });
            this.http.get<any>('http://localhost:8080/session', { headers }).subscribe(
                (response: any) => {
                    if (response[0]>=0) {
                        this.router.navigate(["admin-panel"]);
                    }
                }
            );

        }

    }

}
