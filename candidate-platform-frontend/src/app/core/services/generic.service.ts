import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable} from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class GenericService {
  private baseUrl = 'http://localhost:8090';
  private modals: any[] = [];
  private urlPage = 'http://localhost:8090/users?page='; 

  constructor(private http: HttpClient) {}


  getGeneric(id: number): Observable<Object> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  createGeneric(url,object: Object): Observable<any> {
    return this.http.post(`${this.baseUrl}` + url, object);
  }

  createGenericList(url,any : Array<any>) : Observable<any> {
    return this.http.post(`${this.baseUrl}` + url, any);
  }
       
  updateGeneric(url,id: number, object:Object): Observable<any> {
    return this.http.put(`${this.baseUrl}`+ url +`/${id}`, object);
  }

  deleteGeneric(url,id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}`+ url +`/${id}`, { responseType: 'text' });
  }

  getGenericPage(url,page,size): Observable<any> {
    console.log(`${this.baseUrl}` + url+`?page=${page}&`+`size=${size}`);
    return this.http.get(`${this.baseUrl}` + url+`?page=${page}&`+`size=${size}`);
  }

  getGenericList(url): Observable<any> {
    //console.log(this.cookies.get("token"));
    return this.http.get(`${this.baseUrl}` + url);
  }

  getGenericPagesById(firstParturl,SecondPartUrl,id: number,page,size): Observable<any> { 
    return this.http.get(`${this.baseUrl}`+ firstParturl +`/${id}`+SecondPartUrl + `?page=${page}&`+`size=${size}`);
  }


  getGenericById(url,id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}`+ url +`/${id}`);
  }

  deleteAll(): Observable<any> {
    return this.http.delete(`${this.baseUrl}` + `/delete`, { responseType: 'text' });
  }

  open(id: string) {
    // open modal specified by id
    let modal: any = this.modals.filter(x => x.id === id)[0];
    modal.open();
}

close(id: string) {
    // close modal specified by id
    let modal: any = this.modals.filter(x => x.id === id)[0];
    modal.close();
}

getCandidateByUsername(url,username: string): Observable<any>{
  return this.http.get(`${this.baseUrl}`+ url +`/${username}`)
 }

 updateLoginUser(url,id: number, object) : Observable<Object>{
  return this.http.put(`${this.baseUrl}`+ url +`/${id}`, object);
 }

 getGenericByTwoId(firstParturl,SecondPartUrl,firstIid: number, secondId: number): Observable<any> {
  return this.http.get(`${this.baseUrl}`+ firstParturl +`/${firstIid}`+SecondPartUrl +`/${secondId}`);
}

getGenericByTwoIdByPage(firstParturl,SecondPartUrl,firstIid: number, secondId: number,page,size): Observable<any> { 
  return this.http.get(`${this.baseUrl}`+ firstParturl +`/${firstIid}`+SecondPartUrl +`/${secondId}`+ `?page=${page}&`+`size=${size}`);
}

updateStateUserAccount(url1,id:number,url2,body:any): Observable<any> {
  return this.http.put(`${this.baseUrl}`+ url1 +`/${id}` + url2 ,body);
}
updateShared(url1,id:number,url2,body:any): Observable<any> {
  return this.http.put(`${this.baseUrl}`+ url1 +`/${id}` + url2 ,body);
}
 
deleteWithBody(url,body:any): Observable<any> {
  return this.http.delete(`${this.baseUrl}` + url,body);
}

// getPageClient(url,page:number): Observable<PageClient> {
//   url = this.urlPage;
//   url = url+page +"&size=5";
//   return this.http.get<PageClient>(url).pipe(
//    map(response =>{
//      const data = response;
//      console.log(data.content);
//      return data;
//    })
//   )
//  } 

searchGeneric(url, motCle:String,page,size): Observable<any>{
  return this.http.get(`${this.baseUrl}`+ url +`/${motCle}`+`?page=${page}&`+`size=${size}`);

}


deleteListOfMessages(url,any : Array<any>) : Observable<any> {
  return this.http.post(`${this.baseUrl}` + url, any);
}

}
