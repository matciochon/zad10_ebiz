import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-contact-panel',
  templateUrl: './contact-panel.component.html',
  styleUrls: ['./contact-panel.component.css']
})
export class ContactPanelComponent implements OnInit {
  public email = {
    subject: '',
    body: ''
  };

  constructor() { }

  ngOnInit(): void {
  }

  sendEmail() {
    const subject = encodeURIComponent(this.email.subject);
    const body = encodeURIComponent(this.email.body);
    const mailtoLink = `mailto:example@example.com?subject=${subject}&body=${body}`;
    window.location.href = mailtoLink;
  }
}
