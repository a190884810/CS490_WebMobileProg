import {Component, ElementRef, ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Simple Translator';
  @ViewChild('words') inputWords: ElementRef;
  wordsValue: any;
  wordsTranslate: any;

  constructor(private httpCon: HttpClient) {
  }

//   var postData = {
//     userRoles: ['User', 'Worker'],
//     email: 'email@email.com'
//   };
//
//   const headers: HttpHeaders = new  HttpHeaders();
//   headers.set('Content-Type', 'application/x-www-form-urlencoded');
//
//   this.http.post('/UserRole/SaveUserRoles', postData, { headers: headers })
// .subscribe(result => {
// });

  getTranslation() {
    this.wordsValue = this.inputWords.nativeElement.value;

    if (this.wordsValue != null && this.wordsValue !== '' ) {
      // return this.httpCon.post('https://translate.yandex.net/api/v1.5/tr.json/translate' +
      //   '?key=trnsl.1.1.20190223T165846Z.ef9dbf6883c02101.259b96827af8b041bb95b2a682a983277fddc09c' +
      //   '&text=' + this.words +
      //   '&lang=en-ru' +
      //   '&format=html')
      //   .subscribe(data: any) => {
      //   this.wordsTranslate = data;
      // }
      this.httpCon.get('https://translate.yandex.net/api/v1.5/tr.json/translate' +
        '?key=trnsl.1.1.20190223T165846Z.ef9dbf6883c02101.259b96827af8b041bb95b2a682a983277fddc09c' +
        '&text=' + this.wordsValue +
        '&lang=en-de' +
        '&format=plain')
        .subscribe((response: any) => {
          this.wordsTranslate = response.text[0];
          console.log(response);
          console.log(JSON.stringify(response));
          console.log(response.text);
          console.log(JSON.stringify(response.text[0]));

          // for (var i = 0; i < data.response.venues.length; i++) {
          //   this.venueList[i] = {
          //     'name': data.response.venues[i].name,
          //     'id': data.response.venues[i].id,
          //     'location': data.response.venues[i].location
          //   };
          //   console.log(this.venueList[i]);

          // }

        });
    }

  }
}
