# DialogFragmentTinyWrapper
Abstracting away complexities of working with dialogs in Android

Using approach described in this project one can forget about such thing as managing DialogFragments state on activity recreation (it may be caused by device rotation, for example)
By managing state I mean:
<ul>
<li>dialog recreation</li>
<li>dialog listeners reattachment</li>
<li>persistence of data needed for DialogFragment</li>
</ul>

See example and lib code for more info
