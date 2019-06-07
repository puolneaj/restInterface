/**
 * Build the RESTful interface.<br>
 *
 * <p>The set of classes implements a REST architecture which communicates wih Actico Server.<br>
 * Actico Server - located on localhost:8087 - is dedicated to the executions of the business rules.<br>
 * The content of the requests and their results cannot be stored on Actico Servers. <br>
 * For this reason, a RESTful interface is built on localhost:8080 with more flexibility and allow us to
 * store the requests to Actico servers as well as the responses.<br>
 * At the moment, the architecture is thought over an unique business rules however the structure is able to deal <br>
 * with multiple types of business rules.</p>
 *
 * REST architecture revolves around two main concepts:
 *  <ul>
 * <li>Resource: information in REST, it can be a document, an image, a collection of other resources...</li>
 * <li>Resource Methods: used to perform the desired transition. Usually, HTTP GET/PUT/POST/DELETE methods.</li>
 *</ul>
 *
 *
 * <p>Note: REST != HTTP</p>
 *
 *
 */
package com.actico.restInterface;