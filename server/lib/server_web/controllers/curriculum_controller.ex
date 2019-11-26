defmodule ServerWeb.CurriculumController do
  use ServerWeb, :controller

  import Ecto.Query, only: [from: 2]

  alias Server.JobServer
  alias Server.JobServer.Curriculum
  alias Server.Repo

  action_fallback ServerWeb.FallbackController

  def index(conn, _params) do
    curriculuns = JobServer.list_curriculuns()
    render(conn, "index.json", curriculuns: curriculuns)
  end

  def create(conn, %{"curriculum" => curriculum_params}) do
    with {:ok, %Curriculum{} = curriculum} <- JobServer.create_curriculum(curriculum_params) do
      conn
      |> put_status(:created)
      |> put_resp_header("location", Routes.curriculum_path(conn, :show, curriculum))
      |> text("Inserido com sucesso")
    end
  end

  def show(conn, %{"id" => id}) do
    curriculum = JobServer.get_curriculum!(id)
    render(conn, "show.json", curriculum: curriculum)
  end

  def update(conn, %{"id" => id, "curriculum" => curriculum_params}) do
    curriculum = JobServer.get_curriculum!(id)

    with {:ok, %Curriculum{} = curriculum} <- JobServer.update_curriculum(curriculum, curriculum_params) do
      text(conn, "Atualizado com sucesso")
    end
  end

  def delete(conn, %{"id" => id}) do
    curriculum = JobServer.get_curriculum!(id)

    with {:ok, %Curriculum{}} <- JobServer.delete_curriculum(curriculum) do
      send_resp(conn, :no_content, "")
    end
  end

  def getFilter(conn, %{"area" => area}) do
    list = Repo.all(from u in Curriculum, where: u.area == ^area)
    json(conn, %{"curriculuns" => list})
  end
end
