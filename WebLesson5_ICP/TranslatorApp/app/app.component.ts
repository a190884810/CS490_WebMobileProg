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

  getTranslation() {
    this.wordsValue = this.inputWords.nativeElement.value;

    if (this.wordsValue != null && this.wordsValue !== '' ) {
      this.httpCon.get('https://translate.yandex.net/api/v1.5/tr.json/translate' +
        '?key=trnsl.1.1.20190223T165846Z.ef9dbf6883c02101.259b96827af8b041bb95b2a682a983277fddc09c' +
        '&text=' + this.wordsValue +
        '&lang=en-de' +
        '&format=plain')
        .subscribe((response: any) => {
          this.wordsTranslate = response.text[0];
        });
    }
  }
}
