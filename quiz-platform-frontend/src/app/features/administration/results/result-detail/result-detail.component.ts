import { Component, OnInit, Input } from '@angular/core';
import { GenericService } from '@services/generic.service';
import { Quiz } from '@models/quiz';
import { Logo } from 'src/app/features/administration/quizzes/logo';
import * as pdfMake from 'pdfmake/build/pdfmake';
import * as pdfFonts from 'pdfmake/build/vfs_fonts.js';
import { OnlineInterviewQuizResult } from '@models/online-interview-quiz-result';
import { Candidate } from '@models/candidate';


@Component({
  selector: 'app-result-detail',
  templateUrl: './result-detail.component.html',
  styleUrls: ['./result-detail.component.css']
})
export class ResultDetailComponent implements OnInit {

  // Get Quiz to display form quizzes list component
  @Input() resultDetails: any;

  // Configuration params for pdf file
  pageSize = 'A4';
  pageOrientation = 'portrait';
  docDefinition: any = {
    pageSize: this.pageSize,
    pageOrientation: this.pageOrientation,
    content: [],
    styles: {}
  };

  // Description of question
  description: String;

  // Proposition of question
  proposition: String;

  constructor(private genericService: GenericService) { }

  ngOnInit() {
    this.resultDetails = new OnlineInterviewQuizResult;
  }


  /** Add image in pdf file */
  addImage(url: any, width?: number, height?: number) {
    // Add image with new width size
    if (width) {
      // Add image with new height size
      if (height) {
        this.docDefinition.content.push({
          image: url, height: height, width: width,
          alignment: 'center', margin: [0, 5, 0, 5],
        });
      } else {
        // Add image with original height size
        this.docDefinition.content.push({
          image: url, width: width,
          alignment: 'center', margin: [0, 5, 0, 5],
        });
      }
    } else if (height) {
      // Add image with new height size
      this.docDefinition.content.push({
        image: url, heigth: height,
        alignment: 'center', margin: [0, 5, 0, 5],
      });
    } else {
      // Add image with original dimensions
      this.docDefinition.content.push({
        image: url,
        alignment: 'center', margin: [0, 5, 0, 5],
      });
    }
  }

  /** Create pdf file with specefic name */
  createPdf(name?: string) {
    pdfMake.createPdf(this.docDefinition).download(name);
  }

  /** Add style to pdf file */
  configureStyles(styles) {
    this.docDefinition.styles = styles;
  }

  /** Add text to pdf file */
  addText(text: string, style?: string) {
    if (style) {
      this.docDefinition.content.push({ text: text, style: style });
      return;
    }
    this.docDefinition.content.push(text);
  }

  /** Add item to the list in pdf file */
  addUnorderedlist(items: any[]) {
    this.docDefinition.content.push({ ul: items });
  }

  /** To divide file header */
  addColumns(columnsText: any[]) {
    const columns = [];
    columns.push({ image: columnsText[0], width: 150, marginButton: 2000, marginLeft: -10 });
    columns.push({ stack: [{ text: columnsText[1], style: 'h1' }] });
    columns.push({ stack: [{ text: columnsText[2], style: 'h2' }], marginTop: 10 });
    this.docDefinition.content.push({ columns: columns });
  }

  /** To divide file header */
  addThreeColumns(columnsText: any[]) {
    const columns = [];
    columns.push({ stack: [{ text: columnsText[0], style: 'h2' }] });
    columns.push({ stack: [{ text: columnsText[1], style: 'h1' }] });
    columns.push({ stack: [{ text: columnsText[2], style: 'h2' }] });
    this.docDefinition.content.push({ columns: columns });
  }

  /** To divide file header */
  addTwoColumns(columnsText: any[]) {
    const columns = [];
    columns.push({ stack: [{ text: columnsText[0], style: 'h2' }] });
    columns.push({ stack: [{ text: columnsText[1], style: 'h2' }], marginTop: 10 });
    this.docDefinition.content.push({ columns: columns });
  }

  /** get image domensions */
  getDimensions(base64) {
    // Create original image
    const img = new Image();
    img.src = base64;
    return [img.width, img.height];
  }

  /** Create and download pdf file */
  downloadPDF(onlineInterviewQuizResult: OnlineInterviewQuizResult, candidate: Candidate) {
    pdfMake.vfs = pdfFonts.pdfMake.vfs;
    const titre: any[] = [];
    const candidateInfo: any[] = [];
    const dateInfo: any[] = [];
    const note: any[] = [];
    let prop: any[] = [];
    let i = 1;
    // configure pdf style
    this.configureStyles({
      question: { margin: [0, 5, 0, 5], fontSize: 14, bold: true, marginLeft: 5 },
      h1: { margin: [0, 10, 0, 0], fontSize: 20, bold: true, alignment: 'center' },
      h2: { margin: [30, 0, 0, 0], fontSize: 10, bold: true, alignment: 'left' },
      prop: { fontSize: 12, marginLeft: 30, marginTop: 10, marginButton: 10 },

      correctAnsweredProp: { fontSize: 12, marginLeft: 30, marginTop: 10, marginButton: 10, color: 'green' },
      incorrectAnsweredProp: { fontSize: 12, marginLeft: 30, marginTop: 10, marginButton: 10, color: 'red' },

      titre: { bold: true, margin: [0, 30, 0, 1], decorationStyle: 'solid', fontSize: 16 }, spaceQuestion: { margin: [0, 0, 0, 10], }
    });

    const details: any[] = [];
    titre.push(onlineInterviewQuizResult.quiz.title.toString());
    // Get file name
    const nomTest = onlineInterviewQuizResult.quiz.title.toString();
    // Add header strucrture
    details.push('Technologie: ' + onlineInterviewQuizResult.quiz.technology.title.toString() + '\n');
    details.push('Niveau: ' + onlineInterviewQuizResult.quiz.degree.title.toString() + '\n');
    details.push('Durée: ' + onlineInterviewQuizResult.quiz.duration.toString() + ' minutes \n');
    let columns: any[];
    const imageAltran = new Logo();
    columns = [
      imageAltran.src,
      titre,
      details,
    ];
    this.addColumns(columns);
    let columnsCandidate: any[];
    candidateInfo.push('\n' + 'Candidat: ' + candidate.firstName.toString() + ' ' + candidate.lastName.toString()
      + '\n' + 'Profil: ' + candidate.profil + '\n');
    note.push('Note: ' + onlineInterviewQuizResult.note + ' %');
    dateInfo.push('\n' + 'Date de passage: ' + onlineInterviewQuizResult.interviewDate +
      '\n' + 'Durée de passage: ' + onlineInterviewQuizResult.duration + ' minutes \n');
    columnsCandidate = [
      candidateInfo,
      note,
      dateInfo,
    ];
    this.addThreeColumns(columnsCandidate);
    this.addText(' ', 'titre');
    let picture: any;
    let element: any;
    // Insert questions statement to the pdf file
    onlineInterviewQuizResult.passedQuestions.forEach(question => {
      this.description = question.description;
      // Add question text to the pdf file
      this.addText('Question ' + i, 'question');
      const description = this.description.toString();
      const re = /<\/p>|<p>/;
      const split = description.split(re);
      split.forEach(word => {
        // Retrieve picture from the img balise to add in the pdf file
        if (word.includes('<img src="')) {
          element = word;
          picture = element.replace('<img src="', '');
          picture = picture.replace('">', '');
          const dim = this.getDimensions(picture);
          let w, h: number;
          h = dim[1];
          w = dim[0];
          if (dim[0] > 520) { w = 520; }
          if (dim[1] > 250) { h = 250; }
          this.addImage(picture, w, h);
        } else {
          // Replace "<br>" balise by return to line
          if (word.includes('<br>')) { word.replace('<br>', '\n'); }
          this.addText(word);
        }
      });
      // Add question propositions to the pdf file
      question.answeredProposition.forEach(p => {
        this.proposition = p.description;
        if (p.checked && p.valid) {
          prop.push({ text: this.proposition.toString() + ' Correct', style: 'correctAnsweredProp' });
        } else if (p.checked && !p.valid) {
          prop.push({ text: this.proposition.toString(), style: 'incorrectAnsweredProp' });
        } else {
          prop.push({ text: this.proposition.toString(), style: 'prop' });
        }
      });
      i = i + 1;
      this.addUnorderedlist(prop);
      // Style to separate between questions
      this.addText(' ', 'spaceQuestion');
      prop = [];
    });
    // Pdf creation
    this.createPdf(nomTest + '.pdf');
    // Empty the pdf structure to not spoil next pdf downloads
    this.docDefinition.content.length = 0;
  }

}
