;; Se da un arbore de tipul (1). Sa se afiseze calea de la radacina pana la un nod x dat.


;; subarb_stang(l1,l2,..ln,nv,nm) = 
;;	{ nil , daca n=0
;;	{ nil , daca nv > nm
;;	{ l1 U l2 U subarb_stang(l3..ln,nv+1,nm+l2), altfel
;; 
(defun subarb_stang(l nv nm)
    (cond
    ((null l) nil)
    ((> nv nm) nil)
    (t (cons (car l) (cons (cadr l) (subarb_stang (cddr l) (+ nv 1) (+ nm (cadr l))))))
    )
)


;; stang(l1,l2,..ln) = subarb_stang(l3..ln,0,0)
;;
(defun stang(l)
    (subarb_stang (cddr l) 0 0)
)


;; subarb_drept(l1,l2,..ln, nv, nm) = 
;;	{ nil 		, daca n=0
;;	{ l1,l2..ln	, daca nv > nm
;;	{ subarb_drept(l3,..ln, nv+1, nm+l2), altfel
;;  
(defun subarb_drept(l nv nm)
    (cond
    ((null l) nil)
    ((> nv nm) l)
    (t (subarb_drept (cddr l) (+ nv 1) (+ nm (cadr l))))
    )
)



;; drept(l1,l2,..ln) = subarb_drept(l3..ln,0,0)
;;
(defun drept(l)
    (subarb_drept (cddr l) 0 0)
)


;; membru(e, l1,l2..ln) =
;;	{ nil , daca n = 0
;;	{ true,	daca l1 = e
;; 	{ membru(e, l2,l3..ln)
;;
(defun membru (element list)
  (cond
    ((null list) nil)
    ((eql element (car list)) t)
    (t (membru element (cdr list)))))


;; cale(l1,l2,..ln, e) = 
;;	{ (e)	, daca l1 = e
;;	{ l1 U cale(stang(l1,l2,..ln), e), daca membru(e, stang(l1,l2,..ln)) = true)
;;	{ l1 U cale(drept(l1,l2,..ln), e), altfel	
;;
(defun cale (list element)
  (cond 
    ((eql element (car list)) (list element))
    ((membru element (stang list)) (cons (car list) (cale (stang list) element)))
    ((membru element (drept list)) (cons (car list) (cale (drept list) element)))
    (t nil)))

;; lista test '(A 2 B 1 H 1 J 0 C 2 D 2 F 1 K 0 G 0 E 0)
