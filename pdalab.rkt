;Amani Islam

#lang racket

(define (PDA input Sigma Gamma S s0 delta F)

  (define (is-member value list)
    (cond
      ((empty? list) #f)
      ((equal? (first list) value) #t)
      (else
       (is-member value (rest list)))))
  
  (define (subPDA input Sigma Gamma S current delta F pos stack)

    (define (finalstate? stt)
      (cond
        ((or (null? stack) (null? stt)) #f)
        ((and (equal? "$" (car stack)) (is-member (car stt) F)) #t)
        (else (finalstate? (cdr stt)))))

    (define (getcurrinput input p)
      (cond
        ((>= p (string-length input))
         "")
        (else
         (~a (string-ref input p)))))

    (define (stacked curr del in stack)
      
      (define (mutator delt)

        (define (push mini)
          (cond
            ((equal? "" (cadddr delt))
             mini)
            (else
             (cons (cadddr delt) mini))))
        
        (cond
          ((equal? "" (caddr delt))
           (push stack))
          ((list-prefix? (list (caddr delt)) stack)
           (push (list-tail stack 1)))
          (else
           '())))
      
      (cond
        ((null? del) '())
        ((equal? "$" (caddar del))
         stack)
        ((equal? "" (cadar del))
         (stacked (list (caadr del)) (cdr del) in stack))
        ((equal? (caar del) (car curr))
         (cond
           ((equal? in (cadar del))
            (mutator (car del)))
           ((equal? "" (cadadr del))
            (stacked (cadr (cdddar (cdr del))) (cddr del) in stack))
           ((equal? (car curr) (caadr del))
            (stacked curr (cdr del) in stack))
           (else
            '())))
        
        (else
         (stacked curr (cdr del) in stack))))
        
    (define (nextstate curr del in)
      (cond
        
        ((null? del) '())
        
        ((equal? (caar del) (car curr))
         (cond
           ((equal? "" in)
            (caddr (cddadr del)))
           ((or (equal? in (cadar del)) (equal? "" (cadar del)))
            (cadr (cdddar del)))
           ((equal? "" (cadadr del))
            (nextstate (cadr (cdddar del)) (cdr del) in))
           ((equal? (car curr) (caadr del))
            (nextstate curr (cdr del) in))
           (else
            '())))
        
        (else
         (nextstate curr (cdr del) in))))
 
    (cond
      ;((= pos 2)
       ;current)
      ((>= pos (string-length input))
       (finalstate? (nextstate current delta (getcurrinput input pos))))
      
      (else
       (subPDA input Sigma Gamma S (nextstate current delta (getcurrinput input pos)) delta F (add1 pos)
                                   (stacked current delta (getcurrinput input pos) stack)))))
       ;(λ (currstate) (subPDA input Sigma Gamma S (nextstate currstate delta (getcurrinput input pos)) delta F (add1 pos)
        ;                           (stacked currstate delta (getcurrinput input pos) stack))) current)))

  (subPDA input Sigma Gamma S (cons s0 null) delta F 0 '("$")))
  ;(is-member #t (flatten (subPDA input Sigma Gamma S (cons s0 null) delta F 0 '("$")))))


(PDA "000111" '("1" "0") '("$" "0") '(A B C D) 'A '((A "" "" "$" (B)) (B "0" "" "0" (B)) (B "" "" "" (C)) (C"1" "0" "" (C)) (C "" "$" "" (D))) '(D))
;(PDA "0011" '("1" "0") '("$" "0") '(A B C D) 'A '((A "" "" "$" (B)) (B "0" "" "0" (B)) (B "1" "0" "" (C)) (C "1" "0" "" (C)) (C "" "$" "" (D))) '(D))
