import javax.ws.rs.{GET, Path, Produces}

import akka.http.scaladsl.server.Route
import io.swagger.oas.annotations.Operation
import io.swagger.oas.annotations.media.{Content, Schema}
import io.swagger.oas.annotations.responses.ApiResponse

/**
  * Created by lth09 on 11/11/2017.
  */
@Path("/private")
trait RouterDocs {

  @Path("/status")
  @GET
  @Operation(
    description = "Get status of the service",
    responses = Array(new ApiResponse(responseCode = "200", description = "OK", content = Array(new Content(schema = new Schema(implementation = classOf[String])))))
  )
  @Produces(Array("text/plain; charset=UTF-8"))
  def status: Route

  @Path("/Lotto")
  @GET
  @Operation(description = "Get Lotto numbers", responses = Array(new ApiResponse(responseCode = "204")))
  @Produces(Array("application/json; charset=UTF-8"))

  def lotto: Route
}

@Path("/generate")
trait LotteryDocs {
  @Path("/Lotto")
  @GET
  @Operation(description = "Get Lotto numbers", responses = Array(new ApiResponse(responseCode = "204")))
  @Produces(Array("application/json; charset=UTF-8"))
  def lotto: Route
}
